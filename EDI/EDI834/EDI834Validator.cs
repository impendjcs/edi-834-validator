using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using EDI.EDI834.Models;
using EDI.EDI834.Validators;
using EDI.EDI834.Factories;
using EDI.EDI834.Interfaces;
using EDI.EDI834.Exceptions;

namespace EDI.EDI834
{
    public class EDI834Validator
    {
        private readonly Dictionary<string, EDISegmentValidator> _validators;
        private readonly Dictionary<string, EDISegmentFactory> _factories;

        public EDI834Validator()
        {
            _validators = new Dictionary<string, EDISegmentValidator>
            {
                { "ST", new STValidator() },
                { "BGN", new BGNValidator() },
                { "REF", new REFValidator() },
                { "DTP", new DTPValidator() },
                { "N1", new N1Validator() },
                { "INS", new INSValidator() },
                { "REF", new REFValidator() },
                { "DTP", new DTPValidator() },
                { "NM1", new NM1Validator() },
                { "PER", new PERValidator() },
                { "N3", new N3Validator() },
                { "N4", new N4Validator() },
                { "DMG", new DMGValidator() },
                { "HD", new HDValidator() },
                { "DTP", new DTPValidator() },
                { "AMT", new AMTValidator() },
                { "REF", new REFValidator() },
                { "LS", new LSValidator() },
                { "LE", new LEValidator() },
                { "SE", new SEValidator() }
            };

            _factories = new Dictionary<string, EDISegmentFactory>
            {
                { "ST", new STFactory() },
                { "BGN", new BGNFactory() },
                { "REF", new REFFactory() },
                { "DTP", new DTPFactory() },
                { "N1", new N1Factory() },
                { "INS", new INSFactory() },
                { "REF", new REFFactory() },
                { "DTP", new DTPFactory() },
                { "NM1", new NM1Factory() },
                { "PER", new PERFactory() },
                { "N3", new N3Factory() },
                { "N4", new N4Factory() },
                { "DMG", new DMGFactory() },
                { "HD", new HDFactory() },
                { "DTP", new DTPFactory() },
                { "AMT", new AMTFactory() },
                { "REF", new REFFactory() },
                { "LS", new LSFactory() },
                { "LE", new LEFactory() },
                { "SE", new SEFactory() }
            };
        }

        public List<ValidationError> Validate(string ediContent)
        {
            var errors = new List<ValidationError>();
            var segments = ediContent.Split(new[] { '\n', '\r' }, StringSplitOptions.RemoveEmptyEntries);

            foreach (var segment in segments)
            {
                var segmentId = segment.Split('*')[0];
                if (_validators.TryGetValue(segmentId, out var validator))
                {
                    var segmentErrors = validator.Validate(segment);
                    errors.AddRange(segmentErrors);
                }
            }

            return errors;
        }

        public EDI834Model Parse(string ediContent)
        {
            var model = new EDI834Model();
            var segments = ediContent.Split(new[] { '\n', '\r' }, StringSplitOptions.RemoveEmptyEntries);

            foreach (var segment in segments)
            {
                var segmentId = segment.Split('*')[0];
                if (_factories.TryGetValue(segmentId, out var factory))
                {
                    factory.Create(segment, model);
                }
            }

            return model;
        }
    }
} 